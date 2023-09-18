package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScionOfOpulence extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.VAMPIRE, "nontoken Vampire you control");
    private static final FilterControlledPermanent filter2
            = new FilterControlledArtifactPermanent("artifacts");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public ScionOfOpulence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Scion of Opulence or another nontoken Vampire you control dies, create a Treasure token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false, filter
        ));

        // {R}, Sacrifice two artifacts: Exile the top card of your library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1), new ManaCostsImpl<>("{R}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter2)));
        this.addAbility(ability);
    }

    private ScionOfOpulence(final ScionOfOpulence card) {
        super(card);
    }

    @Override
    public ScionOfOpulence copy() {
        return new ScionOfOpulence(this);
    }
}
