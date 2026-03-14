package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.UUID;

public class HeliodTheRadiantDawn extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterEnchantmentCard("enchantment card that isn't a God");
    private static final FilterCard flashFilter = new FilterNonlandCard("spells");

    static {
        filter.add(Predicates.not(SubType.GOD.getPredicate()));
    }

    public HeliodTheRadiantDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{W}{W}",
                "Heliod, the Warped Eclipse",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.GOD}, "WU"
        );

        // Heliod, the Radiant Dawn
        this.getLeftHalfCard().setPT(4, 4);

        // When Heliod, the Radiant Dawn enters the battlefield, return target enchantment card that isn't a God from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.getLeftHalfCard().addAbility(ability);

        // {3}{U/P}: Transform Heliod, the Radiant Dawn. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{U/P}")));

        // Heliod, the Warped Eclipse
        this.getRightHalfCard().setPT(4, 6);

        // You may cast spells as though they had flash.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, flashFilter)));

        // Spells you cast cost {1} less to cast for each card your opponents have drawn this turn.
        Ability ability2 = new SimpleStaticAbility(new HeliodTheWarpedEclipseEffect());
        ability2.addWatcher(new CardsDrawnThisTurnWatcher());
        this.getRightHalfCard().addAbility(ability2);
    }

    private HeliodTheRadiantDawn(final HeliodTheRadiantDawn card) {
        super(card);
    }

    @Override
    public HeliodTheRadiantDawn copy() {
        return new HeliodTheRadiantDawn(this);
    }
}

class HeliodTheWarpedEclipseEffect extends CostModificationEffectImpl {

    HeliodTheWarpedEclipseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "spells you cast cost {1} less to cast for each card your opponents have drawn this turn";
    }

    private HeliodTheWarpedEclipseEffect(final HeliodTheWarpedEclipseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        int amount = game
                .getOpponents(source.getControllerId())
                .stream()
                .mapToInt(watcher::getCardsDrawnThisTurn)
                .sum();
        if (amount < 1) {
            return false;
        }
        CardUtil.adjustCost((SpellAbility) abilityToModify, amount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && game.getCard(abilityToModify.getSourceId()) != null
                && abilityToModify.isControlledBy(source.getControllerId());
    }

    @Override
    public HeliodTheWarpedEclipseEffect copy() {
        return new HeliodTheWarpedEclipseEffect(this);
    }
}
