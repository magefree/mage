package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class RotHulk extends CardImpl {

    private static final FilterCard filterZombie = new FilterCard("Zombie cards from your graveyard");

    static {
        filterZombie.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    private final UUID entersBattlefieldAbilityID;

    public RotHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Rot Hulk enters the battlefield, return up to X target Zombie cards from your graveyard to the battlefield, where X is the number of opponents you have.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("return up to X target Zombie cards from your graveyard to the battlefield, where X is the number of opponents you have.");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetCardInYourGraveyard());
        entersBattlefieldAbilityID = ability.getOriginalId(); // adjust targets
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(entersBattlefieldAbilityID)) {
            // up to X target Zombie cards from your graveyard
            // X is the number of opponents you have.
            ability.getTargets().clear();
            int numbTargets = OpponentsCount.instance.calculate(game, ability, null);
            ability.addTarget(new TargetCardInYourGraveyard(0, numbTargets, filterZombie));
        }
    }

    public RotHulk(final RotHulk card) {
        super(card);
        this.entersBattlefieldAbilityID = card.entersBattlefieldAbilityID;
    }

    @Override
    public RotHulk copy() {
        return new RotHulk(this);
    }
}
