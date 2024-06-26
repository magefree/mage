package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class RotHulk extends CardImpl {
    private static final FilterCard filterZombie = new FilterCard("Zombie cards from your graveyard");

    static {
        filterZombie.add(SubType.ZOMBIE.getPredicate());
    }

    public RotHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Rot Hulk enters the battlefield, return up to X target Zombie cards from your graveyard to the battlefield, where X is the number of opponents you have.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return up to X target Zombie cards from your graveyard to the battlefield, where X is the number of opponents you have."));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filterZombie));
        ability.setTargetAdjuster(new TargetsCountAdjuster(OpponentsCount.instance));
        this.addAbility(ability);
    }

    private RotHulk(final RotHulk card) {
        super(card);
    }

    @Override
    public RotHulk copy() {
        return new RotHulk(this);
    }
}
