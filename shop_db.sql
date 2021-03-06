PGDMP                         z            shop_db    9.6.0    14.1     e           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            f           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            g           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            h           1262    16672    shop_db    DATABASE     d   CREATE DATABASE shop_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE shop_db;
                postgres    false            ?            1259    16673 	   consumers    TABLE     ?   CREATE TABLE public.consumers (
    consumer_id integer NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL
);
    DROP TABLE public.consumers;
       public            postgres    false            ?            1259    16691    consumer_id_seq    SEQUENCE     x   CREATE SEQUENCE public.consumer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.consumer_id_seq;
       public          postgres    false    185            i           0    0    consumer_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.consumer_id_seq OWNED BY public.consumers.consumer_id;
          public          postgres    false    188            ?            1259    16678    products    TABLE     ?   CREATE TABLE public.products (
    product_id integer NOT NULL,
    product_name character varying(100) NOT NULL,
    price integer NOT NULL
);
    DROP TABLE public.products;
       public            postgres    false            ?            1259    16694    product_id_seq    SEQUENCE     w   CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.product_id_seq;
       public          postgres    false    186            j           0    0    product_id_seq    SEQUENCE OWNED BY     J   ALTER SEQUENCE public.product_id_seq OWNED BY public.products.product_id;
          public          postgres    false    189            ?            1259    16683 	   purchases    TABLE     ?   CREATE TABLE public.purchases (
    purchases_id integer NOT NULL,
    consumer_id integer NOT NULL,
    product_id integer NOT NULL,
    date date NOT NULL
);
    DROP TABLE public.purchases;
       public            postgres    false            ?            1259    16696    purchases_id_seq    SEQUENCE     y   CREATE SEQUENCE public.purchases_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.purchases_id_seq;
       public          postgres    false    187            k           0    0    purchases_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.product_id;
          public          postgres    false    190            ?           2604    16693    consumers consumer_id    DEFAULT     t   ALTER TABLE ONLY public.consumers ALTER COLUMN consumer_id SET DEFAULT nextval('public.consumer_id_seq'::regclass);
 D   ALTER TABLE public.consumers ALTER COLUMN consumer_id DROP DEFAULT;
       public          postgres    false    188    185            ?           2604    16698    products product_id    DEFAULT     q   ALTER TABLE ONLY public.products ALTER COLUMN product_id SET DEFAULT nextval('public.product_id_seq'::regclass);
 B   ALTER TABLE public.products ALTER COLUMN product_id DROP DEFAULT;
       public          postgres    false    189    186            ?           2604    16699    purchases purchases_id    DEFAULT     v   ALTER TABLE ONLY public.purchases ALTER COLUMN purchases_id SET DEFAULT nextval('public.purchases_id_seq'::regclass);
 E   ALTER TABLE public.purchases ALTER COLUMN purchases_id DROP DEFAULT;
       public          postgres    false    190    187            ]          0    16673 	   consumers 
   TABLE DATA           G   COPY public.consumers (consumer_id, first_name, last_name) FROM stdin;
    public          postgres    false    185   ?       ^          0    16678    products 
   TABLE DATA           C   COPY public.products (product_id, product_name, price) FROM stdin;
    public          postgres    false    186   ?       _          0    16683 	   purchases 
   TABLE DATA           P   COPY public.purchases (purchases_id, consumer_id, product_id, date) FROM stdin;
    public          postgres    false    187   ?       l           0    0    consumer_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.consumer_id_seq', 8, true);
          public          postgres    false    188            m           0    0    product_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.product_id_seq', 5, true);
          public          postgres    false    189            n           0    0    purchases_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.purchases_id_seq', 17, true);
          public          postgres    false    190            ?           2606    16677    consumers consumer_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.consumers
    ADD CONSTRAINT consumer_pkey PRIMARY KEY (consumer_id);
 A   ALTER TABLE ONLY public.consumers DROP CONSTRAINT consumer_pkey;
       public            postgres    false    185            ?           2606    16682    products products_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (product_id);
 @   ALTER TABLE ONLY public.products DROP CONSTRAINT products_pkey;
       public            postgres    false    186            ?           2606    16687    purchases purchases_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (purchases_id);
 B   ALTER TABLE ONLY public.purchases DROP CONSTRAINT purchases_pkey;
       public            postgres    false    187            ?           2606    16788     purchases fk_consumers_purchases    FK CONSTRAINT     ?   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT fk_consumers_purchases FOREIGN KEY (consumer_id) REFERENCES public.consumers(consumer_id) NOT VALID;
 J   ALTER TABLE ONLY public.purchases DROP CONSTRAINT fk_consumers_purchases;
       public          postgres    false    2017    187    185            ?           2606    16793    purchases fk_products_purchases    FK CONSTRAINT     ?   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT fk_products_purchases FOREIGN KEY (product_id) REFERENCES public.products(product_id) NOT VALID;
 I   ALTER TABLE ONLY public.purchases DROP CONSTRAINT fk_products_purchases;
       public          postgres    false    187    2019    186            ]   ?   x?UN9?@??? ?#?4P??H@?`Z?d!o?{?????x?9ᆨ?M<xA????w??$\????pG4>????`Qb?OO#?5?bgo??B_E/D??qCГ;[?s??`ɯ??ζDr?X???Θ?o??      ^   Z   x?ʫ?0@???H4C1????'"?Ojx??ζ?ɛ?	^Łf[?2?W???ROb???*]????*??Qx?`P? ?8,?      _   [   x?U?Q
? C???8??S????c?@??~?????,Υ⭨??uk?,p$R?O8!???0?CX	0:<???IlQ"?T???h?=??x. /?u"     