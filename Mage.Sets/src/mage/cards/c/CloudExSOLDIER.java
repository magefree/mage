package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEquipmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InsectToken;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;

/**
 *
 * @author balazskristof
 */
public final class CloudExSOLDIER extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("Equipment you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("equipped attacking creature you control");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent();

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
        filter2.add(Predicates.and(
            AttackingPredicate.instance,
            EquippedPredicate.instance
        ));
        filter3.add(new PowerPredicate(ComparisonType.MORE_THAN, 6));
    }

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CloudExSOLDIER(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Cloud enters, attach up to one target Equipment you control to it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CloudExSOLDIEREntersEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Whenever Cloud attacks, draw a card for each equipped attacking creature you control. Then if Cloud has power 7 or greater, create two Treasure tokens.
        Ability ability2 = new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter2)));
        ability2.addEffect(new ConditionalOneShotEffect(
            new CreateTokenEffect(new TreasureToken(), 2), 
            new SourceMatchesFilterCondition(filter3),
            "Then if {this} has power 7 or greater, create two Treasure tokens."
        ));
        this.addAbility(ability2);
    }

    private CloudExSOLDIER(final CloudExSOLDIER card) {
        super(card);
    }

    @Override
    public CloudExSOLDIER copy() {
        return new CloudExSOLDIER(this);
    }
}


class CloudExSOLDIEREntersEffect extends OneShotEffect {

    CloudExSOLDIEREntersEffect() {
        super(Outcome.Benefit);
        staticText = "attach up to one target Equipment you control to {this}";
    }

    private CloudExSOLDIEREntersEffect(final CloudExSOLDIEREntersEffect effect) {
        super(effect);
    }

    @Override
    public CloudExSOLDIEREntersEffect copy() {
        return new CloudExSOLDIEREntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Permanent creature = source.getSourcePermanentIfItStillExists(game);
        return equipment != null && creature != null && creature.addAttachment(equipment.getId(), source, game);
    }
}
