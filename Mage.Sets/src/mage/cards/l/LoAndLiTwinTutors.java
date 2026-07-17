package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoAndLiTwinTutors extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Lesson or Noble card");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent(SubType.NOBLE, "");
    private static final FilterNonlandCard filter3 = new FilterNonlandCard("Lesson spells you control");

    static {
        filter.add(Predicates.or(
                SubType.LESSON.getPredicate(),
                SubType.NOBLE.getPredicate()
        ));
        filter3.add(SubType.LESSON.getPredicate());
    }

    public LoAndLiTwinTutors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Lo and Li enter, search your library for a Lesson or Noble card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ).setTriggerPhrase("When {this} enter, "));

        // Noble creatures you control and Lesson spells you control have lifelink.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        ).setText("Noble creatures you control"));
        ability.addEffect(new GainAbilityControlledSpellsEffect(
                LifelinkAbility.getInstance(), filter3
        ).concatBy("and"));
        this.addAbility(ability);
    }

    private LoAndLiTwinTutors(final LoAndLiTwinTutors card) {
        super(card);
    }

    @Override
    public LoAndLiTwinTutors copy() {
        return new LoAndLiTwinTutors(this);
    }
}
