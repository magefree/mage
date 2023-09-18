package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;

import java.util.UUID;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author LevelX2
 */
public final class AngelOfSerenity extends CardImpl {

    private static final String rule = "you may exile up to three other target creatures " +
            "from the battlefield and/or creature cards from graveyards.";
    
    private static final FilterCreatureCard filterCreatureCard = new FilterCreatureCard("creature card in a graveyard");
    
    private static final FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent("other target creature");
    
    static {
        filterCreaturePermanent.add(AnotherPredicate.instance);
    }

    public AngelOfSerenity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ExileTargetForSourceEffect().setText(rule), true
        );
        ability.addTarget(new TargetCardInGraveyardBattlefieldOrStack(
                0, 3, filterCreatureCard, filterCreaturePermanent
        ));
        this.addAbility(ability);

        // When Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.HAND).withText(true, true), false));
    }

    private AngelOfSerenity(final AngelOfSerenity card) {
        super(card);
    }

    @Override
    public AngelOfSerenity copy() {
        return new AngelOfSerenity(this);
    }
}
