package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwiftReconfiguration extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public SwiftReconfiguration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature or Vehicle
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent is a Vehicle artifact with crew 5 and it loses all other card types.
        this.addAbility(new SimpleStaticAbility(new SwiftReconfigurationEffect()));
    }

    private SwiftReconfiguration(final SwiftReconfiguration card) {
        super(card);
    }

    @Override
    public SwiftReconfiguration copy() {
        return new SwiftReconfiguration(this);
    }
}

class SwiftReconfigurationEffect extends ContinuousEffectImpl {

    SwiftReconfigurationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "enchanted permanent is a Vehicle artifact with crew 5 and it loses all other card types";
    }

    private SwiftReconfigurationEffect(final SwiftReconfigurationEffect effect) {
        super(effect);
    }

    @Override
    public SwiftReconfigurationEffect copy() {
        return new SwiftReconfigurationEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent aura = source.getSourcePermanentIfItStillExists(game);
        if (aura == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(aura.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.ARTIFACT);
                permanent.addSubType(game, SubType.VEHICLE);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(new CrewAbility(5), source.getSourceId(), game);
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
