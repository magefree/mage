package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
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
        this.addAbility(new DiesSourceTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT)).setTriggerPhrase("When this creature dies, "));
    }

    private ImpToken(final ImpToken token) {
        super(token);
    }

    public ImpToken copy() {
        return new ImpToken(this);
    }
}
