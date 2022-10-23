package mage.cards.o;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TrollWarriorToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OldGrowthTroll extends CardImpl {

    public OldGrowthTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Old-Growth Troll dies, if it was a creature, return it to the battlefield. It's an Aura enchantment with enchant Forest you control and "Enchanted Forest has '{T}: Add {G}{G}' and '{1}, {T}, Sacrifice this land: Create a 4/4 green Troll Warrior creature token with trample.'"
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new OldGrowthTrollReturnEffect()),
                OldGrowthTrollCondition.instance, "When {this} dies, if it was a creature, " +
                "return it to the battlefield. It's an Aura enchantment with enchant Forest you control " +
                "and \"Enchanted Forest has '{T}: Add {G}{G}' and '{1}, {T}, Sacrifice this land: " +
                "Create a tapped 4/4 green Troll Warrior creature token with trample.'\""
        ));
    }

    private OldGrowthTroll(final OldGrowthTroll card) {
        super(card);
    }

    @Override
    public OldGrowthTroll copy() {
        return new OldGrowthTroll(this);
    }
}

enum OldGrowthTrollCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getEffects().get(0).getValue("permanentLeftBattlefield");
        return permanent != null && permanent.isCreature(game);
    }
}

class OldGrowthTrollReturnEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FOREST);

    OldGrowthTrollReturnEffect() {
        super(Outcome.PutCardInPlay);
    }

    private OldGrowthTrollReturnEffect(final OldGrowthTrollReturnEffect effect) {
        super(effect);
    }

    @Override
    public OldGrowthTrollReturnEffect copy() {
        return new OldGrowthTrollReturnEffect(this);
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
        target.setNotTarget(true);
        if (controller.choose(outcome, target, source, game)
                && game.getPermanent(target.getFirstTarget()) != null) {
            game.getState().setValue("attachTo:" + source.getSourceId(), target.getFirstTarget());
        }
        game.addEffect(new OldGrowthTrollContinuousEffect(game.getState().getZoneChangeCounter(source.getSourceId()) + 1), source);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent aura = game.getPermanent(card.getId());
        Permanent creature = game.getPermanent(target.getFirstTarget());
        if (aura == null || creature == null) {
            return true;
        }
        creature.addAttachment(aura.getId(), source, game);
        return true;
    }
}

class OldGrowthTrollContinuousEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FOREST);
    private final int zoneChangeCounter;

    OldGrowthTrollContinuousEffect(int zoneChangeCounter) {
        super(Duration.Custom, Outcome.Neutral);
        this.zoneChangeCounter = zoneChangeCounter;
        dependencyTypes.add(DependencyType.AuraAddingRemoving);
    }

    private OldGrowthTrollContinuousEffect(final OldGrowthTrollContinuousEffect ability) {
        super(ability);
        this.zoneChangeCounter = ability.zoneChangeCounter;
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
        Permanent troll = sourceObject;
        switch (layer) {
            case TypeChangingEffects_4:
                troll.removeAllCardTypes(game);
                troll.addCardType(game, CardType.ENCHANTMENT);
                troll.removeAllSubTypes(game);
                troll.addSubType(game, SubType.AURA);
                break;
            case AbilityAddingRemovingEffects_6:
                // Spell Ability can be null with clone effects (ex. Moritte)
                if (troll.getSpellAbility() == null) {
                    troll.addAbility(new SpellAbility(null, null), source.getSourceId(), game);
                }
                troll.getSpellAbility().getTargets().clear();
                troll.getSpellAbility().getEffects().clear();
                TargetPermanent auraTarget = new TargetPermanent(filter);
                troll.getSpellAbility().addTarget(auraTarget);
                troll.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
                troll.addAbility(new EnchantAbility(auraTarget), source.getSourceId(), game);

                // add the activated ability
                troll.addAbility(makeAbility(), source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.AbilityAddingRemovingEffects_6;
    }

    @Override
    public OldGrowthTrollContinuousEffect copy() {
        return new OldGrowthTrollContinuousEffect(this);
    }

    private static final Ability makeAbility() {
        Ability activatedAbility = new SimpleActivatedAbility(
                new CreateTokenEffect(new TrollWarriorToken(), 1, true, false), new GenericManaCost(1)
        );
        activatedAbility.addCost(new TapSourceCost());
        Cost cost = new SacrificeSourceCost();
        cost.setText("sacrifice this land");
        activatedAbility.addCost(cost);
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(new SimpleManaAbility(
                Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()), AttachmentType.AURA
        ).setText("enchanted Forest has \"{T}: Add {G}{G}\""));
        ability.addEffect(new GainAbilityAttachedEffect(
                activatedAbility, AttachmentType.AURA
        ).setText("and \"{1}, {T}, Sacrifice this land: Create a tapped 4/4 green Troll Warrior creature token with trample.\""));
        return ability;
    }
}
