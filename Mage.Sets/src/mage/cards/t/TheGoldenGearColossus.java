package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.DoubleFacedCardPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.GnomeToken;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class TheGoldenGearColossus extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("other target double-faced artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.and(
                DoubleFacedCardPredicate.instance,
                CardType.ARTIFACT.getPredicate()
        )
        );
    }

    public TheGoldenGearColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, null);
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.nightCard = true;

        this.color.setBlue(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        // Whenever The Golden-Gear Colossus enters the battlefield or attacks, transform up to one other target double-faced artifact you control. Create two 1/1 colorless Gnome artifact creature tokens.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new TransformTargetEffect());
        TargetPermanent target = new TargetPermanent(0, 1, filter);
        ability.addTarget(target);
        ability.addEffect(new CreateTokenEffect(new GnomeToken(), 2));
        this.addAbility(ability);

    }

    private TheGoldenGearColossus(final TheGoldenGearColossus card) {
        super(card);
    }

    @Override
    public TheGoldenGearColossus copy() {
        return new TheGoldenGearColossus(this);
    }
}
