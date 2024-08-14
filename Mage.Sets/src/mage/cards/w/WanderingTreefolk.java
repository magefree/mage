package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.costadjusters.DomainAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SeekCardEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import java.util.UUID;

/**
 *
 * @author karapuzz14
 */
public final class WanderingTreefolk extends CardImpl {

    public WanderingTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Domain -- {7}{G}: Seek a creature card. This ability costs {1} less to activate for each basic land type among lands you control.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new SeekCardEffect(StaticFilters.FILTER_CARD_CREATURE),
                new ManaCostsImpl<>("{7}{G}")
        );

        ability.addEffect(new InfoEffect("This ability costs {1} less to activate " +
                "for each basic land type among lands you control."));
        ability.addHint(DomainHint.instance);
        ability.setAbilityWord(AbilityWord.DOMAIN);
        ability.setCostAdjuster(DomainAdjuster.instance);

        this.addAbility(ability);
    }

    private WanderingTreefolk(final WanderingTreefolk card) {
        super(card);
    }

    @Override
    public WanderingTreefolk copy() {
        return new WanderingTreefolk(this);
    }
}