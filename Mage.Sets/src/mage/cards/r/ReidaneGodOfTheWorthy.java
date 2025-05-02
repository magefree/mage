package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BecomesTargetControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReidaneGodOfTheWorthy extends ModalDoubleFacedCard {

    private static final FilterPermanent filter = new FilterLandPermanent("snow lands your opponents control");
    private static final FilterPermanent filterAnother = new FilterControlledPermanent("another permanent you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filterAnother.add(AnotherPredicate.instance);
    }

    public ReidaneGodOfTheWorthy(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{W}",
                "Valkmira, Protector's Shield",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}{W}"
        );

        // 1.
        // Reidane, God of the Worthy
        // Legendary Creature - God
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(3));

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Snow lands your opponents control enter the battlefield tapped.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));

        // Noncreature spells your opponents cast with converted mana cost 4 or more cost {2} more to cast.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new ReidaneGodOfTheWorthyCostEffect()));

        // 2.
        // Valkmira, Protector's Shield
        // Legendary Artifact
        // If a source an opponent controls would deal damage to you or a permanent you control, prevent 1 of that damage.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ValkmiraProtectorsShieldPreventionEffect()));

        // Whenever you or another permanent you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {1}.
        this.getRightHalfCard().addAbility(new BecomesTargetControllerTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(1))
                .setText("counter that spell or ability unless its controller pays {1}"),
                filterAnother, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.SPELL, false));
    }

    private ReidaneGodOfTheWorthy(final ReidaneGodOfTheWorthy card) {
        super(card);
    }

    @Override
    public ReidaneGodOfTheWorthy copy() {
        return new ReidaneGodOfTheWorthy(this);
    }
}

class ReidaneGodOfTheWorthyCostEffect extends CostModificationEffectImpl {

    ReidaneGodOfTheWorthyCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Noncreature spells your opponents cast with mana value 4 or greater cost {2} more to cast";
    }

    private ReidaneGodOfTheWorthyCostEffect(ReidaneGodOfTheWorthyCostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((!(abilityToModify instanceof SpellAbility))
                || !game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return spellCard != null && !spellCard.isCreature(game) && spellCard.getManaValue() >= 4;
    }

    @Override
    public ReidaneGodOfTheWorthyCostEffect copy() {
        return new ReidaneGodOfTheWorthyCostEffect(this);
    }
}

class ValkmiraProtectorsShieldPreventionEffect extends PreventionEffectImpl {

    ValkmiraProtectorsShieldPreventionEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "If a source an opponent controls would deal damage to you or a permanent you control, prevent 1 of that damage";
    }

    private ValkmiraProtectorsShieldPreventionEffect(ValkmiraProtectorsShieldPreventionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                boolean isOpponent = game.getOpponents(game.getControllerId(event.getSourceId())).contains(source.getControllerId());
                return isOpponent
                        && source.isControlledBy(event.getTargetId())
                        && super.applies(event, source, game);
            case DAMAGE_PERMANENT:
                isOpponent = game.getOpponents(game.getControllerId(event.getSourceId())).contains(source.getControllerId());
                Permanent permanent = game.getPermanent(event.getTargetId());
                return isOpponent
                        && permanent != null
                        && permanent.isControlledBy(source.getControllerId())
                        && super.applies(event, source, game);
        }
        return false;
    }

    @Override
    public ValkmiraProtectorsShieldPreventionEffect copy() {
        return new ValkmiraProtectorsShieldPreventionEffect(this);
    }
}
