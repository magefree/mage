package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SeekCardEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

public final class ExcogitatorSphinx extends CardImpl {

    public ExcogitatorSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more creatures you control deal combat damage to a player, investigate.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(new InvestigateEffect()));

        // {1}, Sacrifice a Clue: Seek an instant or sorcery card.
        Ability ability = new SimpleActivatedAbility(
                new SeekCardEffect(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY)
                        .setText("seek an instant or sorcery card"), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CLUE));
        this.addAbility(ability);
    }

    private ExcogitatorSphinx(final ExcogitatorSphinx card) {
        super(card);
    }

    @Override
    public ExcogitatorSphinx copy() {
        return new ExcogitatorSphinx(this);
    }
}
