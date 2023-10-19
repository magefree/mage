package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class GlimmerpointStag extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GlimmerpointStag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Glimmerpoint Stag enters the battlefield, exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new ExileReturnBattlefieldNextEndStepTargetEffect());
        etbAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(etbAbility);
    }

    private GlimmerpointStag(final GlimmerpointStag card) {
        super(card);
    }

    @Override
    public GlimmerpointStag copy() {
        return new GlimmerpointStag(this);
    }
}
