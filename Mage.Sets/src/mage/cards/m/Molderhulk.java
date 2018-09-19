package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.SourceCostReductionForEachCardInGraveyardEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class Molderhulk extends CardImpl {

    private static final FilterCard filter
            = new FilterLandCard("land card from your graveyard");

    public Molderhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Undergrowth — This spell costs {1} less to cast for each creature card in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SourceCostReductionForEachCardInGraveyardEffect(StaticFilters.FILTER_CARD_CREATURE));
        ability.setAbilityWord(AbilityWord.UNDERGROWTH);
        this.addAbility(ability);

        // When Molderhulk enters the battlefield, return target land card from your graveyard to the battlefield.
        ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public Molderhulk(final Molderhulk card) {
        super(card);
    }

    @Override
    public Molderhulk copy() {
        return new Molderhulk(this);
    }
}
