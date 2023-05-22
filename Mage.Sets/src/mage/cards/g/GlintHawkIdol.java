

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author Loki
 */
public final class GlintHawkIdol extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("another artifact");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public GlintHawkIdol (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        
        // Whenever another artifact enters the battlefield under your control, you may have {this} become a 2/2 Bird artifact creature with flying until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new GlintHawkIdolToken(), CardType.ARTIFACT, Duration.EndOfTurn), filter, true));

        // {W}: Glint Hawk Idol becomes a 2/2 Bird artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new GlintHawkIdolToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.W)));
    }

    public GlintHawkIdol (final GlintHawkIdol card) {
        super(card);
    }

    @Override
    public GlintHawkIdol copy() {
        return new GlintHawkIdol(this);
    }

}

class GlintHawkIdolToken extends TokenImpl {
    GlintHawkIdolToken() {
        super("", "2/2 Bird artifact creature with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
    }
    public GlintHawkIdolToken(final GlintHawkIdolToken token) {
        super(token);
    }

    public GlintHawkIdolToken copy() {
        return new GlintHawkIdolToken(this);
    }
}
