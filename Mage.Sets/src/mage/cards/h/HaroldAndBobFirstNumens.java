package mage.cards.h;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class HaroldAndBobFirstNumens extends CardImpl {

    public HaroldAndBobFirstNumens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Harold and Bob, First Numens dies, if it was a creature, return it to the battlefield. It's an Aura enchantment with enchant Forest you control and "Enchanted Forest has '{T}: Add three mana of any one color. You get two rad counters.'" Harold and Bob loses all other abilities.
        this.addAbility(new DiesSourceTriggeredAbility(new HaroldAndBobReturnEffect())
            .withInterveningIf(HaroldAndBobWasCreatureCondition.instance));
    }

    private HaroldAndBobFirstNumens(final HaroldAndBobFirstNumens card) {
        super(card);
    }

    @Override
    public HaroldAndBobFirstNumens copy() {
        return new HaroldAndBobFirstNumens(this);
    }
}

enum HaroldAndBobWasCreatureCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
            .getEffectValueFromAbility(source, "permanentLeftBattlefield", Permanent.class)
            .filter(permanent -> permanent.isCreature(game))
            .isPresent();
    }

    @Override
    public String toString() {
        return "it was a creature";
    }
}

class HaroldAndBobReturnEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FOREST);

    HaroldAndBobReturnEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield. It's an Aura enchantment with enchant Forest you control "
                + "and \"Enchanted Forest has '{T}: Add three mana of any one color. You get two rad counters.'\" "
                + "Harold and Bob loses all other abilities.";
    }

    private HaroldAndBobReturnEffect(final HaroldAndBobReturnEffect effect) {
        super(effect);
    }

    @Override
    public HaroldAndBobReturnEffect copy() {
        return new HaroldAndBobReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !(game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD)) {
            return false;
        }

        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        if (controller.choose(outcome, target, source, game)
                && game.getPermanent(target.getFirstTarget()) != null) {
            game.getState().setValue("attachTo:" + source.getSourceId(), target.getFirstTarget());
        }
        game.addEffect(new HaroldAndBobContinuousEffect(game.getState().getZoneChangeCounter(source.getSourceId()) + 1), source);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent aura = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        Permanent forest = game.getPermanent(target.getFirstTarget());
        if (aura == null || forest == null) {
            return true;
        }
        forest.addAttachment(aura.getId(), source, game);
        return true;
    }
}

class HaroldAndBobContinuousEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FOREST);
    private final int zoneChangeCounter;

    HaroldAndBobContinuousEffect(int zoneChangeCounter) {
        super(Duration.Custom, Outcome.Neutral);
        this.zoneChangeCounter = zoneChangeCounter;
        dependencyTypes.add(DependencyType.AuraAddingRemoving);
    }

    private HaroldAndBobContinuousEffect(final HaroldAndBobContinuousEffect effect) {
        super(effect);
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (game.getState().getZoneChangeCounter(source.getSourceId()) > zoneChangeCounter) {
            discard();
            return false;
        }
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) {
            sourceObject = game.getPermanentEntering(source.getSourceId());
        }
        if (sourceObject == null) {
            return false;
        }
        Permanent harold = sourceObject;
        switch (layer) {
            case TypeChangingEffects_4:
                harold.removeAllCardTypes(game);
                harold.addCardType(game, CardType.ENCHANTMENT);
                harold.removeAllSubTypes(game);
                harold.addSubType(game, SubType.AURA);
                break;
            case AbilityAddingRemovingEffects_6:
                // Spell Ability can be null with clone effects (ex. Moritte)
                if (harold.getSpellAbility() == null) {
                    harold.addAbility(new SpellAbility(null, null), source.getSourceId(), game);
                }
                List<Ability> toRemove = new ArrayList<>();
                for (Ability ability : harold.getAbilities(game)) {
                    if (!harold.getSpellAbility().equals(ability)) {
                        toRemove.add(ability);
                    }
                }
                harold.removeAbilities(toRemove, source.getSourceId(), game);
                harold.getSpellAbility().getTargets().clear();
                harold.getSpellAbility().getEffects().clear();
                TargetPermanent auraTarget = new TargetPermanent(filter);
                harold.getSpellAbility().addTarget(auraTarget);
                harold.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
                harold.addAbility(new EnchantAbility(auraTarget), source.getSourceId(), game);
                harold.addAbility(makeAbility(), source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.AbilityAddingRemovingEffects_6;
    }

    @Override
    public HaroldAndBobContinuousEffect copy() {
        return new HaroldAndBobContinuousEffect(this);
    }

    private static Ability makeAbility() {
        SimpleManaAbility manaAbility = new SimpleManaAbility(
            Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost()
        );
        manaAbility.addEffect(
            new AddCountersPlayersEffect(CounterType.RAD.createInstance(2), TargetController.YOU)
                    .setText("you get two rad counters")
        );
        return new SimpleStaticAbility(
            new GainAbilityAttachedEffect(manaAbility, AttachmentType.AURA)
                .setText("enchanted Forest has \"{T}: Add three mana of any one color. You get two rad counters.\"")
        );
    }
}
