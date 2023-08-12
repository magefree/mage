package mage.cards.m;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.CastCardFromGraveyardThenExileItEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MavindaStudentsAdvocate extends CardImpl {

    public MavindaStudentsAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {0}: You may cast target instant or sorcery card from your graveyard this turn. If that spell doesn't target a creature you control, it costs {8} more to cast this way. If that spell would be put into your graveyard, exile it instead. Activate only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new MavindaStudentsAdvocateEffect(), new GenericManaCost(0)
        );
        ability.addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));
        this.addAbility(ability);
    }

    private MavindaStudentsAdvocate(final MavindaStudentsAdvocate card) {
        super(card);
    }

    @Override
    public MavindaStudentsAdvocate copy() {
        return new MavindaStudentsAdvocate(this);
    }
}

class MavindaStudentsAdvocateEffect extends CastCardFromGraveyardThenExileItEffect {

    MavindaStudentsAdvocateEffect() {
        super();
        staticText = "you may cast target instant or sorcery card from your graveyard this turn. " +
                "If that spell doesn't target a creature you control, it costs {8} more to cast this way. " +
                "If that spell would be put into your graveyard, exile it instead";
    }

    private MavindaStudentsAdvocateEffect(final MavindaStudentsAdvocateEffect effect) {
        super(effect);
    }

    @Override
    public MavindaStudentsAdvocateEffect copy() {
        return new MavindaStudentsAdvocateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!super.apply(game, source)) {
            return false;
        }
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        game.addEffect(new MavindaStudentsAdvocateCostEffect(card, game), source);
        return true;
    }
}

class MavindaStudentsAdvocateCostEffect extends CostModificationEffectImpl {

    private final MageObjectReference mor;

    MavindaStudentsAdvocateCostEffect(Card card, Game game) {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.INCREASE_COST);
        mor = new MageObjectReference(card, game, 1);
    }

    private MavindaStudentsAdvocateCostEffect(MavindaStudentsAdvocateCostEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 8);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId())
                && mor.refersTo(CardUtil.getMainCardId(game, abilityToModify.getSourceId()), game)
                && abilityToModify
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isCreature(game))
                .map(Controllable::getControllerId)
                .noneMatch(source::isControlledBy);
    }

    @Override
    public MavindaStudentsAdvocateCostEffect copy() {
        return new MavindaStudentsAdvocateCostEffect(this);
    }
}
