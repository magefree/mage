
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.CaribouToken;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class CaribouRange extends CardImpl {

    static FilterControlledPermanent filter = new FilterControlledPermanent("a Caribou token");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(SubType.CARIBOU.getPredicate());
    }

    public CaribouRange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant land you control
        TargetPermanent auraTarget = new TargetPermanent(new FilterControlledLandPermanent());
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted land has "{W}{W}, {T}: Create a 0/1 white Caribou creature token."
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new CaribouToken()), new ManaCostsImpl<>("{W}{W}"));
        ability.addCost(new TapSourceCost());
        Effect effect = new GainAbilityAttachedEffect(ability, AttachmentType.AURA);
        effect.setText("Enchanted land has \"{W}{W}, {T}: Create a 0/1 white Caribou creature token.\"");
        this.addAbility(new SimpleStaticAbility(effect));
        // Sacrifice a Caribou token: You gain 1 life.
        this.addAbility(new SimpleActivatedAbility(new GainLifeEffect(1),
                new SacrificeTargetCost(filter)));
    }

    private CaribouRange(final CaribouRange card) {
        super(card);
    }

    @Override
    public CaribouRange copy() {
        return new CaribouRange(this);
    }
}
