package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PearlEarImperialAdvisor extends CardImpl {

    // Same as Mycosynth Golem, Nonland seems to be to not display the ability on lands.
    private static final FilterNonlandCard filter = new FilterNonlandCard("Enchantment spells you cast");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    private static final FilterControlledPermanent filterPermanentAura = new FilterControlledPermanent(SubType.AURA, "Auras");
    private static final Hint hint = new ValueHint(
            "Auras you control", new PermanentsOnBattlefieldCount(filterPermanentAura)
    );

    private static final FilterPermanent filterModified = new FilterControlledPermanent();
    private static final FilterSpell filterAura = new FilterSpell("an Aura spell that targets a modified permanent you control");

    static {
        filterModified.add(ModifiedPredicate.instance);
        filterAura.add(SubType.AURA.getPredicate());
        filterAura.add(new TargetsPermanentPredicate(filterModified));
    }

    public PearlEarImperialAdvisor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Enchantment spells you cast have affinity for Auras.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(
                        new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filterPermanentAura)).addHint(hint),
                        filter
                )
        ));

        // Whenever you cast an Aura spell that targets a modified permanent you control, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filterAura, false));
    }

    private PearlEarImperialAdvisor(final PearlEarImperialAdvisor card) {
        super(card);
    }

    @Override
    public PearlEarImperialAdvisor copy() {
        return new PearlEarImperialAdvisor(this);
    }
}
