package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 * @author LoneFox
 */
public final class TheEleventhHourToken extends TokenImpl {

    private static final FilterCard filter = new FilterCard("Doctor spells you cast");

    static {
        filter.add(SubType.DOCTOR.getPredicate());
    }

    public TheEleventhHourToken() {
        super("Human Token", "1/1 white Human creature token with \"Doctor spells you cast cost 1 less to cast.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    protected TheEleventhHourToken(final TheEleventhHourToken token) {
        super(token);
    }

    @Override
    public TheEleventhHourToken copy() {
        return new TheEleventhHourToken(this);
    }
}
