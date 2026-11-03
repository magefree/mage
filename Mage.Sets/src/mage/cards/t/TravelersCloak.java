package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.ChooseLandTypeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class TravelersCloak extends CardImpl {

    public TravelersCloak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As Traveler's Cloak enters the battlefield, choose a land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseLandTypeEffect(Outcome.AddAbility)));

        // When Traveler's Cloak enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // Enchanted creature has landwalk of the chosen type.
        Effect effect = new TravelersCloakGainAbilityAttachedEffect();
        effect.setText("Enchanted creature has landwalk of the chosen type");
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private TravelersCloak(final TravelersCloak card) {
        super(card);
    }

    @Override
    public TravelersCloak copy() {
        return new TravelersCloak(this);
    }
}

class TravelersCloakGainAbilityAttachedEffect extends GainAbilityAttachedEffect {

    TravelersCloakGainAbilityAttachedEffect() {
        super(new LandwalkAbility(new FilterControlledLandPermanent("chosen type")), AttachmentType.AURA);
    }

    protected TravelersCloakGainAbilityAttachedEffect(final TravelersCloakGainAbilityAttachedEffect effect) {
        super(effect);
    }

    @Override
    public TravelersCloakGainAbilityAttachedEffect copy() {
        return new TravelersCloakGainAbilityAttachedEffect(this);
    }

    @Override
    protected List<Ability> getAbilitiesToGrant(Game game, Ability source) {
        // the land type is chosen on the Aura, but the granted ability is evaluated against the
        // creature, so the predicate has to carry the Aura's id rather than read its own source
        FilterControlledLandPermanent filter = new FilterControlledLandPermanent("chosen type");
        filter.add(new ChosenLandTypePredicate(source.getSourceId()));
        return Collections.singletonList(new LandwalkAbility(filter));
    }
}

class ChosenLandTypePredicate implements Predicate<MageObject> {

    private final UUID auraId;

    ChosenLandTypePredicate(UUID auraId) {
        this.auraId = auraId;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(auraId, game);
        return input.hasSubtype(subType, game);
    }

    @Override
    public String toString() {
        return "Chosen subtype";
    }
}
