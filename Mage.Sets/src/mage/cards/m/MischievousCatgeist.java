package mage.cards.m;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MischievousCatgeist extends TransformingDoubleFacedCard {

    public MischievousCatgeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.CAT, SubType.SPIRIT}, "{1}{U}",
                "Catlike Curiosity",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "U"
        );

        // Mischievous Catgeist
        this.getLeftHalfCard().setPT(1, 1);

        // Whenever Mischievous Catgeist deals combat damage to a player, draw card.
        this.getLeftHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));


        // Catlike Curiosity
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {2}{U}
        // needs to be added after enchant ability is set for target
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{2}{U}"));

        // Enchanted creature has "Whenever this creature deals combat damage to a player, draw a card."
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), false
                ).setTriggerPhrase("Whenever this creature deals combat damage to a player, "), AttachmentType.AURA
        )));

        // If Catlike Curiosity would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private MischievousCatgeist(final MischievousCatgeist card) {
        super(card);
    }

    @Override
    public MischievousCatgeist copy() {
        return new MischievousCatgeist(this);
    }
}
