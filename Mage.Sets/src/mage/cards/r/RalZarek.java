package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RalZarek extends CardImpl {

    private static final FilterPermanent secondFilter = new FilterPermanent("another target permanent");

    static {
        secondFilter.add(new AnotherTargetPredicate(2));
    }

    public RalZarek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);

        this.setStartingLoyalty(4);

        // +1: Tap target permanent, then untap another target permanent.
        LoyaltyAbility ability1 = new LoyaltyAbility(new TapTargetEffect(), 1);
        TargetPermanent firstTarget = new TargetPermanent();
        firstTarget.setTargetTag(1);
        ability1.addTarget(firstTarget);
        Effect effect = new UntapTargetEffect();
        effect.setText(", then untap another target permanent");
        effect.setTargetPointer(new SecondTargetPointer());
        ability1.addEffect(effect);
        TargetPermanent secondTarget = new TargetPermanent(secondFilter);
        secondTarget.setTargetTag(2);
        ability1.addTarget(secondTarget);
        this.addAbility(ability1);

        // -2: Ral Zarek deals 3 damage to any target.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DamageTargetEffect(3), -2);
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(ability2);

        // -7: Flip five coins. Take an extra turn after this one for each coin that comes up heads.
        this.addAbility(new LoyaltyAbility(new RalZarekExtraTurnsEffect(), -7));
    }

    private RalZarek(final RalZarek card) {
        super(card);
    }

    @Override
    public RalZarek copy() {
        return new RalZarek(this);
    }
}

class RalZarekExtraTurnsEffect extends OneShotEffect {

    RalZarekExtraTurnsEffect() {
        super(Outcome.ExtraTurn);
        this.staticText = "Flip five coins. Take an extra turn after this one for each coin that comes up heads";
    }

    private RalZarekExtraTurnsEffect(final RalZarekExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public RalZarekExtraTurnsEffect copy() {
        return new RalZarekExtraTurnsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(player -> player
                        .flipCoins(source, game, 5, true)
                        .stream()
                        .mapToInt(x -> x ? 1 : 0)
                        .sum()
                ).orElse(0);
        for (int i = 0; i < amount; i++) {
            game.getState().getTurnMods().add(new TurnMod(source.getControllerId()).withExtraTurn());
        }
        return true;
    }
}
