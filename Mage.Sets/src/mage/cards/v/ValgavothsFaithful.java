package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValgavothsFaithful extends CardImpl {

    public ValgavothsFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{B}, Sacrifice Valgavoth's Faithful: Return target creature card from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private ValgavothsFaithful(final ValgavothsFaithful card) {
        super(card);
    }

    @Override
    public ValgavothsFaithful copy() {
        return new ValgavothsFaithful(this);
    }
}
