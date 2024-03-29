package mage.cards.b;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class BiggerOnTheInside extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact or land");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public BiggerOnTheInside(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact or land
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent has "{T}: Target player adds two mana of any one color. The next spell they cast this turn has cascade."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BiggerOnTheInsideEffect(), new TapSourceCost());
        gainedAbility.addTarget(new TargetPlayer());
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BiggerOnTheInside(final BiggerOnTheInside card) {
        super(card);
    }

    @Override
    public BiggerOnTheInside copy() {
        return new BiggerOnTheInside(this);
    }
}

class BiggerOnTheInsideEffect extends OneShotEffect { //Not a mana ability since it targets

    BiggerOnTheInsideEffect() {
        super(Outcome.Benefit);
        staticText = "Target player adds two mana of any one color. The next spell they cast this turn has cascade";
    }

    private BiggerOnTheInsideEffect(final BiggerOnTheInsideEffect effect) {
        super(effect);
    }

    @Override
    public BiggerOnTheInsideEffect copy() {
        return new BiggerOnTheInsideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        ContinuousEffect cascadeEffect = new NextSpellCastHasAbilityEffect(new CascadeAbility(), StaticFilters.FILTER_CARD, TargetController.SOURCE_TARGETS);
        game.addEffect(cascadeEffect, source);

        ManaEffect manaEffect = new AddManaOfAnyColorEffect(2);
        Mana manaToAdd = manaEffect.produceMana(game, source);
        if (manaToAdd != null && manaToAdd.count() > 0) {
            player.getManaPool().addMana(manaToAdd, game, source);
        }
        return true;
    }
}
