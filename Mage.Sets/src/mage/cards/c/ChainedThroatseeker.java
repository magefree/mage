package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author North
 */
public final class ChainedThroatseeker extends CardImpl {

    public ChainedThroatseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Infect (This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        this.addAbility(InfectAbility.getInstance());

        // Chained Throatseeker can't attack unless defending player is poisoned.
        this.addAbility(new SimpleStaticAbility(new ChainedThroatseekerCantAttackEffect()));
    }

    private ChainedThroatseeker(final ChainedThroatseeker card) {
        super(card);
    }

    @Override
    public ChainedThroatseeker copy() {
        return new ChainedThroatseeker(this);
    }
}

class ChainedThroatseekerCantAttackEffect extends RestrictionEffect {

    ChainedThroatseekerCantAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless defending player is poisoned";
    }

    private ChainedThroatseekerCantAttackEffect(final ChainedThroatseekerCantAttackEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        Player targetPlayer = game.getPlayerOrPlaneswalkerController(defenderId);
        return targetPlayer != null && targetPlayer.getCounters().containsKey(CounterType.POISON);
    }

    @Override
    public ChainedThroatseekerCantAttackEffect copy() {
        return new ChainedThroatseekerCantAttackEffect(this);
    }
}
