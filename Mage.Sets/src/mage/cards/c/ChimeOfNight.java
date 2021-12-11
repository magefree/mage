package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChimeOfNight extends CardImpl {

    public ChimeOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Chime of Night is put into a graveyard from the battlefield, destroy target nonblack creature.
        ability = new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.addAbility(ability);
    }

    private ChimeOfNight(final ChimeOfNight card) {
        super(card);
    }

    @Override
    public ChimeOfNight copy() {
        return new ChimeOfNight(this);
    }
}
