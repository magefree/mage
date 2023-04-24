package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofBaseAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SkrelvDefectorMite extends CardImpl {

    public SkrelvDefectorMite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN, SubType.MITE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // Skrelv, Defector Mite can't block.
        this.addAbility(new CantBlockAbility());

        // {W/P}, {T}: Choose a color. Another target creature you control gains toxic 1 and hexproof from
        // that color until end of turn. It can't be blocked by creatures of that color this turn.
        Ability ability = new SimpleActivatedAbility(new SkrelvDefectorMiteEffect(), new ManaCostsImpl<>("{W/P}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private SkrelvDefectorMite(final SkrelvDefectorMite card) {
        super(card);
    }

    @Override
    public SkrelvDefectorMite copy() {
        return new SkrelvDefectorMite(this);
    }
}

class SkrelvDefectorMiteEffect extends OneShotEffect {

    SkrelvDefectorMiteEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a color. Another target creature you control gains toxic 1 and hexproof from " +
                "that color until end of turn. It can't be blocked by creatures of that color this turn";
    }

    private SkrelvDefectorMiteEffect(final SkrelvDefectorMiteEffect effect) {
        super(effect);
    }

    @Override
    public SkrelvDefectorMiteEffect copy() {
        return new SkrelvDefectorMiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor(true);
        player.choose(outcome, choice, game);
        game.addEffect(new GainAbilityTargetEffect(new ToxicAbility(1), Duration.EndOfTurn), source);
        game.addEffect(new GainAbilityTargetEffect(HexproofBaseAbility.getFirstFromColor(choice.getColor()), Duration.EndOfTurn), source);
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ColorPredicate(choice.getColor()));
        game.addEffect(new CantBeBlockedByAllTargetEffect(filter, Duration.EndOfTurn), source);
        return true;
    }
}
