package mage.cards.j;

import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.abilities.keyword.special.JohanVigilanceAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.NoAbilityPredicate;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

public final class JasmineBorealOfTheSeven extends CardImpl {

    private static final FilterSpell spell_filter
            = new FilterSpell("creature spells with no abilities");
    private static final FilterCreaturePermanent your_creatures_filter
            = new FilterCreaturePermanent("creatures you control with no abilities");
    private static final FilterCreaturePermanent with_abilities_filter
            = new FilterCreaturePermanent("by creatures with abilities");

    static {
        spell_filter.add(NoAbilityPredicate.instance);
        spell_filter.add(CardType.CREATURE.getPredicate());
        your_creatures_filter.add(NoAbilityPredicate.instance);
        your_creatures_filter.add(TargetController.YOU.getControllerPredicate());
        with_abilities_filter.add(new AnyAbilityPredicate());
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

class AnyAbilityPredicate implements Predicate<MageObject> {
    @Override
    public boolean apply(MageObject input, Game game) {
        boolean isFaceDown = false;
        Abilities<Ability> abilities;
        if (input instanceof Card) {
            abilities = ((Card) input).getAbilities(game);
            isFaceDown = ((Card) input).isFaceDown(game);
        } else {
            abilities = input.getAbilities();
        }
        if (isFaceDown) {
            for (Ability ability : abilities) {
                if (ability.getWorksFaceDown()) {
                    // inner face down abilities like turn up and becomes creature
                    continue;
                }
                if (!Objects.equals(ability.getClass(), SpellAbility.class) && !ability.getClass().equals(JohanVigilanceAbility.class)) {
                    return true;
                }
            }
            return false;
        }

        for (Ability ability : abilities) {
            if (!Objects.equals(ability.getClass(), SpellAbility.class) && !ability.getClass().equals(JohanVigilanceAbility.class)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "with abilities";
    }
}