package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAttachedEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class Gigantiform extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Gigantiform");

    static {
        filter.add(new NamePredicate("Gigantiform"));
    }

    public Gigantiform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");
        this.subtype.add(SubType.AURA);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has base power and toughness 8/8 and has trample.
        Ability ability = new SimpleStaticAbility(
                new SetBasePowerToughnessAttachedEffect(8, 8, AttachmentType.AURA)
        );
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.AURA
        ).setText("and has trample"));
        this.addAbility(ability);

        // When Gigantiform enters the battlefield, if it was kicked, you may search your library for a card named Gigantiform, put it onto the battlefield, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), true
        ).withInterveningIf(KickedCondition.ONCE));
    }

    private Gigantiform(final Gigantiform card) {
        super(card);
    }

    @Override
    public Gigantiform copy() {
        return new Gigantiform(this);
    }
}
