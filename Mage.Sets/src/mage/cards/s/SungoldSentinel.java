package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.HexproofBaseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SungoldSentinel extends CardImpl {

    public SungoldSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Sungold Sentinel enters the battlefield or attacks, exile up to one target card from a graveyard.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);

        // Coven — {1}{W}: Choose a color. Sungold Sentinel gains hexproof from that color until end of turn and can't be blocked by creatures of that color this turn. Activate only if you control three or more creatures with different powers.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new SungoldSentinelEffect(),
                new ManaCostsImpl<>("{1}{W}"), CovenCondition.instance
        ).addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private SungoldSentinel(final SungoldSentinel card) {
        super(card);
    }

    @Override
    public SungoldSentinel copy() {
        return new SungoldSentinel(this);
    }
}

class SungoldSentinelEffect extends OneShotEffect {

    SungoldSentinelEffect() {
        super(Outcome.Benefit);
        staticText = "choose a color. {this} gains hexproof from that color until end of turn " +
                "and can't be blocked by creatures of that color this turn";
    }

    private SungoldSentinelEffect(final SungoldSentinelEffect effect) {
        super(effect);
    }

    @Override
    public SungoldSentinelEffect copy() {
        return new SungoldSentinelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor(true);
        player.choose(outcome, choice, game);
        Ability ability = HexproofBaseAbility.getFirstFromColor(choice.getColor());
        game.addEffect(new GainAbilitySourceEffect(ability, Duration.EndOfTurn), source);
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ColorPredicate(choice.getColor()));
        game.addEffect(new CantBeBlockedByAllSourceEffect(filter, Duration.EndOfTurn), source);
        return true;
    }
}
