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
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class TravelersCloak extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("chosen type");

    static {
        filter.add(TravelersCloakChosenSubtypePredicate.instance);
    }

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
        Effect effect = new TravelersCloakGainAbilityAttachedEffect(filter);
        effect.setText("Enchanted creature has landwalk of the chosen type");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
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

    TravelersCloakGainAbilityAttachedEffect(FilterControlledLandPermanent filter) {
        super(new LandwalkAbility(filter), AttachmentType.AURA);
    }

    @Override
    public void afterGain(Game game, Ability source, Permanent permanent, Ability addedAbility) {
        super.afterGain(game, source, permanent, addedAbility);

        // ChooseLandTypeEffect keep settings in original source, but we must transfer it to real permanent
        Object val = game.getState().getValue(source.getSourceId() + "_type");
        game.getState().setValue(permanent.getId() + "_landwalk_type", val);
    }
}

enum TravelersCloakChosenSubtypePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(input.getSourceId(), game, "_landwalk_type");
        return input.getObject().hasSubtype(subType, game);
    }

    @Override
    public String toString() {
        return "Chosen subtype";
    }
}

