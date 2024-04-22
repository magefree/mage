package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class JaceCunningCastawayIllusionToken extends TokenImpl {

    public JaceCunningCastawayIllusionToken() {
        super("Illusion Token", "2/2 blue Illusion creature token with \"When this creature becomes the target of a spell, sacrifice it.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);

        subtype.add(SubType.ILLUSION);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new BecomesTargetSourceTriggeredAbility(new SacrificeSourceEffect().setText("sacrifice it"), StaticFilters.FILTER_SPELL_A)
                .setTriggerPhrase("When this creature becomes the target of a spell, "));
    }

    private JaceCunningCastawayIllusionToken(final JaceCunningCastawayIllusionToken token) {
        super(token);
    }

    @Override
    public JaceCunningCastawayIllusionToken copy() {
        return new JaceCunningCastawayIllusionToken(this);
    }
}
