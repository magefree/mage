package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class JuratiCordialQueen extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("artifact token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public JuratiCordialQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BORG);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more artifact creatures you control deal combat damage to a player, create a token that's a copy of target artifact token you control.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
            new CreateTokenCopyTargetEffect(),
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private JuratiCordialQueen(final JuratiCordialQueen card) {
        super(card);
    }

    @Override
    public JuratiCordialQueen copy() {
        return new JuratiCordialQueen(this);
    }
}
