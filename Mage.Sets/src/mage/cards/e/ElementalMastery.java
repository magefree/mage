
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Elemental11HasteToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ElementalMastery extends CardImpl {

    public ElementalMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has "{tap}: create X 1/1 red Elemental creature tokens with haste, where X is this creature's power. Exile them at the beginning of the next end step."
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElementalMasteryEffect(), new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.AURA)));
    }

    private ElementalMastery(final ElementalMastery card) {
        super(card);
    }

    @Override
    public ElementalMastery copy() {
        return new ElementalMastery(this);
    }
}

class ElementalMasteryEffect extends OneShotEffect {

    public ElementalMasteryEffect() {
        super(Outcome.Benefit);
        staticText = "create X 1/1 red Elemental creature tokens with haste, where X is this creature's power. Exile them at the beginning of the next end step";
    }

    public ElementalMasteryEffect(final ElementalMasteryEffect effect) {
        super(effect);
    }

    @Override
    public ElementalMasteryEffect copy() {
        return new ElementalMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creatureAttached = game.getPermanent(source.getSourceId());
        if (creatureAttached != null) {
            int power = creatureAttached.getPower().getValue();
            if (power > 0) {
                CreateTokenEffect effect = new CreateTokenEffect(new Elemental11HasteToken(), power);
                effect.apply(game, source);
                effect.exileTokensCreatedAtNextEndStep(game, source);
                return true;
            }
        }
        return false;
    }

}
