package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author LevelX2, TheElk801
 */
public final class BronzehideLion extends CardImpl {

    public BronzehideLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {G}{W}: Bronzehide Lion gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{G}{W}")));

        // When Bronzehide Lion dies, return it to the battlefield.
        // It's an Aura enchantment with enchant creature you control and
        // "{G}{W}: Enchanted creature gains indestructible until end of turn," and it loses all other abilities.
        this.addAbility(new DiesSourceTriggeredAbility(new BronzehideLionReturnEffect()));
    }

    private BronzehideLion(final BronzehideLion card) {
        super(card);
    }

    @Override
    public BronzehideLion copy() {
        return new BronzehideLion(this);
    }
}

class BronzehideLionReturnEffect extends OneShotEffect {

    BronzehideLionReturnEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield. It's an Aura enchantment with enchant creature you control "
                + "and \"{G}{W}: Enchanted creature gains indestructible until end of turn,\" and it loses all other abilities.";
    }

    private BronzehideLionReturnEffect(final BronzehideLionReturnEffect effect) {
        super(effect);
    }

    @Override
    public BronzehideLionReturnEffect copy() {
        return new BronzehideLionReturnEffect(this);
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
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (controller.choose(outcome, target, source, game)
                && game.getPermanent(target.getFirstTarget()) != null) {
            game.getState().setValue("attachTo:" + source.getSourceId(), target.getFirstTarget());
        }
        game.addEffect(new BronzehideLionContinuousEffect(game.getState().getZoneChangeCounter(source.getSourceId()) + 1), source);
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

class BronzehideLionContinuousEffect extends ContinuousEffectImpl {

    private final int zoneChangeCounter;
    private final Ability activatedAbility = new SimpleActivatedAbility(new GainAbilityAttachedEffect(
            IndestructibleAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn
    ), new ManaCostsImpl<>("{G}{W}"));

    BronzehideLionContinuousEffect(int zoneChangeCounter) {
        super(Duration.Custom, Outcome.Neutral);
        this.zoneChangeCounter = zoneChangeCounter;
        dependencyTypes.add(DependencyType.AuraAddingRemoving);
    }

    private BronzehideLionContinuousEffect(final BronzehideLionContinuousEffect ability) {
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
        }
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) {
            sourceObject = game.getPermanentEntering(source.getSourceId());
        }
        if (sourceObject == null) {
            return false;
        }
        Permanent lion = sourceObject;
        switch (layer) {
            case TypeChangingEffects_4:
                lion.removeAllCardTypes(game);
                lion.addCardType(game, CardType.ENCHANTMENT);
                lion.removeAllSubTypes(game);
                lion.addSubType(game, SubType.AURA);
                break;
            case AbilityAddingRemovingEffects_6:
                List<Ability> toRemove = new ArrayList<>();
                for (Ability ability : lion.getAbilities(game)) {
                    if (!lion.getSpellAbility().equals(ability)) {
                        toRemove.add(ability);
                    }
                }
                lion.removeAbilities(toRemove, source.getSourceId(), game);

                lion.getSpellAbility().getTargets().clear();
                lion.getSpellAbility().getEffects().clear();
                TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
                lion.getSpellAbility().addTarget(auraTarget);
                lion.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
                lion.addAbility(new EnchantAbility(auraTarget.getTargetName()), source.getSourceId(), game);

                // add the activated ability
                activatedAbility.setControllerId(source.getControllerId());
                lion.addAbility(activatedAbility, source.getSourceId(), game);
                break;
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }

    @Override
    public BronzehideLionContinuousEffect copy() {
        return new BronzehideLionContinuousEffect(this);
    }
}
