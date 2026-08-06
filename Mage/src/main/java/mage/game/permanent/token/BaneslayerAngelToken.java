package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 * @author muz
 */
public final class BaneslayerAngelToken extends TokenImpl {

    private static final FilterPermanent filter = new FilterPermanent("Demons and from Dragons");

    static {
        filter.add(Predicates.or(
            SubType.DEMON.getPredicate(),
            SubType.DRAGON.getPredicate()
        ));
    }

    public BaneslayerAngelToken() {
        super("Baneslayer Angel", "Baneslayer Angel token");
        manaCost = new ManaCostsImpl<>("{3}{W}{W}");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(5);
        toughness = new MageInt(5);

        // Flying, first strike, lifelink, protection from Demons and from Dragons
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
    }

    private BaneslayerAngelToken(final BaneslayerAngelToken token) {
        super(token);
    }

    @Override
    public BaneslayerAngelToken copy() {
        return new BaneslayerAngelToken(this);
    }
}
