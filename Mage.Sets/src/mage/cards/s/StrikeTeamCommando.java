
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Styxo
 */
public final class StrikeTeamCommando extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact without spaceflight");

    static {
        filter.add(Predicates.not(new AbilityPredicate(SpaceflightAbility.class)));
    }

    public StrikeTeamCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G/W}{G/W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Strike Team Commando enters the battlefield, you may destroy target artifact without spaceflight.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetArtifactPermanent(filter));
        this.addAbility(ability);

    }

    private StrikeTeamCommando(final StrikeTeamCommando card) {
        super(card);
    }

    @Override
    public StrikeTeamCommando copy() {
        return new StrikeTeamCommando(this);
    }
}
