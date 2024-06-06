package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TarmogoyfToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author grimreap124
 */
public final class TarmogoyfNest extends CardImpl {

    public TarmogoyfNest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.LHURGOYF);
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted land has "{1}{G}, {T}: Create a Tarmogoyf token."
        Ability gainedAbility = new SimpleActivatedAbility(
                new CreateTokenEffect(new TarmogoyfToken()), new ManaCostsImpl<>("{1}{G}")
        );
        gainedAbility.addCost(new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                gainedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield, null, "land"
        )));

    }

    private TarmogoyfNest(final TarmogoyfNest card) {
        super(card);
    }

    @Override
    public TarmogoyfNest copy() {
        return new TarmogoyfNest(this);
    }
}
