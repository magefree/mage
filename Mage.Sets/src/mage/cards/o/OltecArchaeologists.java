package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
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
public final class OltecArchaeologists extends CardImpl {

    public OltecArchaeologists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Oltec Archaeologists enters the battlefield, choose one --
        // * Return target artifact card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));

        // * Scry 3.
        ability.addMode(new Mode(new ScryEffect(3)));
        this.addAbility(ability);
    }

    private OltecArchaeologists(final OltecArchaeologists card) {
        super(card);
    }

    @Override
    public OltecArchaeologists copy() {
        return new OltecArchaeologists(this);
    }
}
