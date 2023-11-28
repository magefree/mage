package mage.game.permanent.token;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

/**
 * @author Susucr
 */
public final class GnomeSoldierStarStarToken extends TokenImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public GnomeSoldierStarStarToken() {
        super("Gnome Soldier Token", "white Gnome Soldier artifact creature token with "
                + "\"This creature's power and toughness are each equal to the number of artifacts and/or creatures you control.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GNOME);
        subtype.add(SubType.SOLDIER);
        color.setWhite(true);

        this.addAbility(new SimpleStaticAbility(new SetBasePowerToughnessSourceEffect(
                xValue
        ).setText("this creature's power and toughness are each equal to the number of artifacts and/or creatures you control")));
    }

    protected GnomeSoldierStarStarToken(final GnomeSoldierStarStarToken token) {
        super(token);
    }

    public GnomeSoldierStarStarToken copy() {
        return new GnomeSoldierStarStarToken(this);
    }
}
