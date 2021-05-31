package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoughtMonitor extends CardImpl {

    public ThoughtMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Thought Monitor enters the battlefield, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2)));
    }

    private ThoughtMonitor(final ThoughtMonitor card) {
        super(card);
    }

    @Override
    public ThoughtMonitor copy() {
        return new ThoughtMonitor(this);
    }
}
