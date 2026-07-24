package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nandmp
 */
public final class HawkeyeMasterMarksman extends CardImpl {

    public HawkeyeMasterMarksman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trick Arrows -- Whenever Hawkeye becomes tapped, you may pay {1} up to three times. When you do, choose up to that many.
        // * Net -- Target creature can't block this turn.
        // * Explosive -- Hawkeye deals 2 damage to target player.
        // * Boomerang -- Discard a card, then draw a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new HawkeyeMasterMarksmanEffect())
                .withFlavorWord("Trick Arrows"));
    }

    private HawkeyeMasterMarksman(final HawkeyeMasterMarksman card) {
        super(card);
    }

    @Override
    public HawkeyeMasterMarksman copy() {
        return new HawkeyeMasterMarksman(this);
    }
}

class HawkeyeMasterMarksmanEffect extends OneShotEffect {

    private static ReflexiveTriggeredAbility makeAbility() {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), false
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.getModes().getMode().withFlavorWord("Net");

        ability.addMode(new Mode(new DamageTargetEffect(2))
                .addTarget(new TargetPlayer())
                .withFlavorWord("Explosive"));
        ability.addMode(new Mode(new DiscardControllerEffect(1))
                .addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"))
                .withFlavorWord("Boomerang"));
        ability.getModes().setMinModes(0);
        ability.getModes().setChooseText("choose up to that many");
        return ability;
    }

    HawkeyeMasterMarksmanEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {1} up to three times. When you do, " + makeAbility().getRule();
    }

    private HawkeyeMasterMarksmanEffect(final HawkeyeMasterMarksmanEffect effect) {
        super(effect);
    }

    @Override
    public HawkeyeMasterMarksmanEffect copy() {
        return new HawkeyeMasterMarksmanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < 3; i++) {
            Cost cost = new ManaCostsImpl<>("{1}");
            if (!cost.canPay(source, source, source.getControllerId(), game)
                    || !cost.pay(source, game, source, source.getControllerId(), false)) {
                break;
            }
            count++;
        }
        if (count < 1) {
            return false;
        }
        ReflexiveTriggeredAbility ability = makeAbility();
        ability.getModes().setMaxModes(count);
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
