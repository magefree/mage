package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

public final class ImpToken extends TokenImpl {

    public ImpToken() {
        super("Imp Token", "2/2 red Imp creature token with \"When this creature dies, it deals 2 damage to each opponent.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.IMP);
        color.setRed(true);
        power = new MageInt(2);
        toughness = new MageInt(2);

        // When this creature dies, it deals 2 damage to each opponent.
        Effect effect = new DamagePlayersEffect(2, TargetController.OPPONENT);
        effect.setText("it deals 2 damage to each opponent");
        Ability ability = new DiesSourceTriggeredAbility(effect);
        this.addAbility(ability);
    }

    private ImpToken(final ImpToken token) {
        super(token);
    }

    public ImpToken copy() {
        return new ImpToken(this);
    }
}
