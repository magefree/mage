package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NashiMoonsLegacy extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary or Rat card from your graveyard");

    static {
        filter.add(Predicates.or(
                SuperType.LEGENDARY.getPredicate(),
                SubType.RAT.getPredicate()
        ));
    }

    public NashiMoonsLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}"), false));

        // Whenever Nashi, Moon's Legacy attacks, exile up to one target legendary or Rat card from your graveyard and copy it. You may cast the copy.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetCardCopyAndCastEffect(false).setText(
                "exile up to one target legendary or Rat card from your graveyard and copy it. You may cast the copy"));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private NashiMoonsLegacy(final NashiMoonsLegacy card) {
        super(card);
    }

    @Override
    public NashiMoonsLegacy copy() {
        return new NashiMoonsLegacy(this);
    }
}