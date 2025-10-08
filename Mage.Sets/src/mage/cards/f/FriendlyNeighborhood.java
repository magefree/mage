package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.HumanCitizenToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class FriendlyNeighborhood extends CardImpl {

    public FriendlyNeighborhood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, create three 1/1 green and white Human Citizen creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanCitizenToken(), 3)));

        // Enchanted land has "{1}, {T}: Target creature gets +1/+1 until end of turn for each creature you control. Activate only as a sorcery."
        Effect boostEffect = new BoostTargetEffect(CreaturesYouControlCount.SINGULAR, CreaturesYouControlCount.SINGULAR)
                .setText("Target creature gets +1/+1 until end of turn for each creature you control");
        Ability ability = new ActivateAsSorceryActivatedAbility(boostEffect,
                new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(ability, AttachmentType.AURA, Duration.WhileOnBattlefield, null, "land")
                .withQuotes(true)));
    }

    private FriendlyNeighborhood(final FriendlyNeighborhood card) {
        super(card);
    }

    @Override
    public FriendlyNeighborhood copy() {
        return new FriendlyNeighborhood(this);
    }
}
