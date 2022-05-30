package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeaHag extends AdventureCard {

    public SeaHag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{U}", "Aquatic Ingress", "{2}{U}");

        this.subtype.add(SubType.HAG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Sea Hag enters the battlefield, creatures your opponents control get -4/-0 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(
                -4, 0, Duration.EndOfTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        )));

        // Aquatic Ingress
        // Up to two target creatures each get +1/+0 until end of turn and can't be blocked this turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellCard().getSpellAbility().addEffect(new CantBeBlockedTargetEffect().setText("and can't be blocked this turn"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private SeaHag(final SeaHag card) {
        super(card);
    }

    @Override
    public SeaHag copy() {
        return new SeaHag(this);
    }
}
