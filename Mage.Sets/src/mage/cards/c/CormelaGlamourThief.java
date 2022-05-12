package mage.cards.c;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CormelaGlamourThief extends CardImpl {

    public CormelaGlamourThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}, {T}: Add {U}{B}{R}. Spend this mana only to cast instant and/or sorcery spells.
        Ability ability = new ConditionalColoredManaAbility(
                new GenericManaCost(1),
                new Mana(0, 1, 1, 1, 0, 0, 0, 0),
                new InstantOrSorcerySpellManaBuilder()
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // When Cormela, Glamour Thief dies, return up to one target instant or sorcery card from your graveyard to your hand.
        ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));
        this.addAbility(ability);
    }

    private CormelaGlamourThief(final CormelaGlamourThief card) {
        super(card);
    }

    @Override
    public CormelaGlamourThief copy() {
        return new CormelaGlamourThief(this);
    }
}
