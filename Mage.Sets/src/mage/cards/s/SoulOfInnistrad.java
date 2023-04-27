package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SoulOfInnistrad extends CardImpl {

    public SoulOfInnistrad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // {3}{B}{B}: Return up to three target creature cards from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{3}{B}{B}"));
        ability.addTarget(new TargetCardInYourGraveyard(0, 3, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // {3}{B}{B}, Exile Soul of Innistrad from your graveyard: Return up to three target creature cards from your graveyard to your hand.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{3}{B}{B}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, 3, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        this.addAbility(ability);  

    }

    private SoulOfInnistrad(final SoulOfInnistrad card) {
        super(card);
    }

    @Override
    public SoulOfInnistrad copy() {
        return new SoulOfInnistrad(this);
    }
}
