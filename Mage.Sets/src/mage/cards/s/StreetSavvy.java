package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author noahg
 */
public final class StreetSavvy extends CardImpl {

    public StreetSavvy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +0/+2 and can block creatures with landwalk abilities as though they didn't have those abilities.
        SimpleStaticAbility staticAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(0 ,2)
            .setText("Enchanted creature gets +0/+2 and can block creatures with landwalk abilities as though they didn't have those abilities."));
        staticAbility.addEffect(new StreetSavvyEffect());
        this.addAbility(staticAbility);
    }

    private StreetSavvy(final StreetSavvy card) {
        super(card);
    }

    @Override
    public StreetSavvy copy() {
        return new StreetSavvy(this);
    }
}

class StreetSavvyEffect extends AsThoughEffectImpl {

    public StreetSavvyEffect() {
        super(AsThoughEffectType.BLOCK_LANDWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "";
    }

    public StreetSavvyEffect(final StreetSavvyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public StreetSavvyEffect copy() {
        return new StreetSavvyEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        return sourcePermanent != null && sourceId.equals(sourcePermanent.getAttachedTo());
    }
}
