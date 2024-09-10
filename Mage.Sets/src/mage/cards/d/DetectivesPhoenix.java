package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DetectivesPhoenix extends CardImpl {

    public DetectivesPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bestow--{R}, Collect evidence 6
        Ability ability = new BestowAbility(this, "{R}");
        ability.addCost(new CollectEvidenceCost(6));
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Enchanted creature gets +2/+2 and has flying and haste.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(
                new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA)
                        .setText("and has flying")
        );
        ability.addEffect(
                new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA)
                        .setText("and haste")
        );
        this.addAbility(ability);

        // You may cast Detective's Phoenix from your graveyard using its bestow ability.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new DetectivesPhoenixEffect()));
    }

    private DetectivesPhoenix(final DetectivesPhoenix card) {
        super(card);
    }

    @Override
    public DetectivesPhoenix copy() {
        return new DetectivesPhoenix(this);
    }
}

// Similar to Tenacious Underdog
class DetectivesPhoenixEffect extends AsThoughEffectImpl {

    DetectivesPhoenixEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "You may cast {this} from your graveyard using its bestow ability";
    }

    private DetectivesPhoenixEffect(final DetectivesPhoenixEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DetectivesPhoenixEffect copy() {
        return new DetectivesPhoenixEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return objectId.equals(source.getSourceId())
                && source.isControlledBy(playerId)
                && affectedAbility instanceof BestowAbility
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && game.getCard(source.getSourceId()) != null;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}
