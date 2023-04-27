package mage.cards.j;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NoAbilityPredicate;

import java.util.UUID;

public final class JasmineBorealOfTheSeven extends CardImpl {

    private static final FilterSpell spell_filter
            = new FilterSpell("creature spells with no abilities");
    private static final FilterCreaturePermanent your_creatures_filter
            = new FilterCreaturePermanent("creatures you control with no abilities");
    private static final FilterCreaturePermanent with_abilities_filter
            = new FilterCreaturePermanent("creatures with abilities");

    static {
        spell_filter.add(NoAbilityPredicate.instance);
        spell_filter.add(CardType.CREATURE.getPredicate());
        your_creatures_filter.add(NoAbilityPredicate.instance);
        your_creatures_filter.add(TargetController.YOU.getControllerPredicate());
        with_abilities_filter.add(Predicates.not(NoAbilityPredicate.instance));
    }

    public JasmineBorealOfTheSeven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Add {G}{W}. Spend this mana only to cast creature spells with no abilities.
        this.addAbility(new ConditionalColoredManaAbility(
                new Mana(1, 0, 0, 0, 1, 0, 0, 0),
                new ConditionalSpellManaBuilder(spell_filter)
        ));

        // Creatures you control with no abilities can’t be blocked by creatures with abilities.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesAllEffect(
                your_creatures_filter, with_abilities_filter, Duration.WhileOnBattlefield
        )));
    }

    private JasmineBorealOfTheSeven(final JasmineBorealOfTheSeven card) {
        super(card);
    }

    @Override
    public JasmineBorealOfTheSeven copy() {
        return new JasmineBorealOfTheSeven(this);
    }
}