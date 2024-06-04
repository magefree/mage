package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.permanent.token.TarmogoyfToken;
import mage.target.common.TargetLandPermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author grimreap124
 */
public final class TarmogoyfNest extends CardImpl {

    public TarmogoyfNest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.TRIBAL, CardType.ENCHANTMENT }, "{2}{G}");

        this.subtype.add(SubType.LHURGOYF);
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted land has "{1}{G}, {T}: Create a Tarmogoyf token."
        Ability gainedAbility = new SimpleActivatedAbility(new CreateTokenEffect(new TarmogoyfToken()),
                new TapSourceCost());
        gainedAbility.addCost(new ManaCostsImpl<>("{1}{G}"));
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        this.addAbility(new SimpleStaticAbility(effect));

    }

    private TarmogoyfNest(final TarmogoyfNest card) {
        super(card);
    }

    @Override
    public TarmogoyfNest copy() {
        return new TarmogoyfNest(this);
    }
}
